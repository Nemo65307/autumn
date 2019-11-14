import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../user.service';
import {User} from '../User';
import {ActivatedRoute, Router} from '@angular/router';
import {Role} from '../Role';
import {ValidationResult} from './ValidationResult';
import {matchOtherValidator} from './match-other-validator';


@Component({
    selector: 'app-user-create',
    templateUrl: './user-form.component.html',
    styleUrls: ['./user-form.component.css'],
    providers: [UserService]
})
export class UserFormComponent implements OnInit, OnDestroy {

    id: string;
    user: User;
    roles: Role[];

    userForm: FormGroup;
    private sub: any;

    serverValidation: string[];

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService) {
    }

    ngOnInit() {
        this.getAllRoles();

        this.sub = this.route.params.subscribe(params => {
            this.id = params['id'];
        });
        this.userForm = new FormGroup({
            login: new FormControl('', Validators.required),
            email: new FormControl('', [
                Validators.required,
                Validators.pattern('^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$'),
            ]),
            password: new FormControl('', Validators.required),
            passwordAgain: new FormControl('', [
                matchOtherValidator('password'),
            ]),
            firstName: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z]*$'),
            ]),
            lastName: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z]*$'),
            ]),
            birthday: new FormControl('', [
                Validators.required,
                Validators.pattern('^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$')]), //yyyy-mm-dd
            role: new FormControl(null, Validators.required)
        });

        if (this.id) { //edit form
            this.userService.findById(this.id).subscribe(
                user => {
                    this.id = user.id;
                    this.user = user;
                    this.userForm.patchValue({
                        login: user.login,
                        email: user.email,
                        password: '',
                        firstName: user.firstName,
                        lastName: user.lastName,
                        birthday: user.birthday,
                        role: user.role
                    });
                }, error => {
                    console.log(error);
                }
            );
            this.userForm.setControl('password', new FormControl('', Validators.nullValidator));
            this.userForm.controls['login'].disable();
        }

    }

    getAllRoles() {
        this.userService.findAllRoles().subscribe(
            roles => {
                this.roles = roles;
            },
            err => {
                console.log(err);
            }
        );
    }

    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }

    onSubmit() {
        if (this.userForm.valid) {
            if (this.id) {
                let user: User = new User(this.id,
                    this.user.login,
                    this.userForm.controls['email'].value,
                    this.userForm.controls['password'].value,
                    this.userForm.controls['firstName'].value,
                    this.userForm.controls['lastName'].value,
                    this.userForm.controls['birthday'].value,
                    this.userForm.controls['role'].value);
                this.userService.updateUser(user, user.id).subscribe(() => {
                    this.router.navigate(['/admin/users-list'], { skipLocationChange: true });
                }, err => {
                    this.serverValidation = this.parseServerValidation(err);
                });
            } else {
                let user: User = new User(null,
                    this.userForm.controls['login'].value,
                    this.userForm.controls['email'].value,
                    this.userForm.controls['password'].value,
                    this.userForm.controls['firstName'].value,
                    this.userForm.controls['lastName'].value,
                    this.userForm.controls['birthday'].value,
                    this.userForm.controls['role'].value);
                this.userService.saveUser(user).subscribe(() => {
                    this.router.navigate(['/admin/users-list'], { skipLocationChange: true });
                }, err => {
                    this.serverValidation = this.parseServerValidation(err);
                });
            }
        }
    }

    redirectUserPage() {
        this.router.navigate(['/admin/users-list'], { skipLocationChange: true });
    }

    compareRoles(role1: Role, role2: Role): boolean {
        if (role2) {
            return role1.name === role2.name;
        }
        return false;
    }

    private parseServerValidation(err: any): string[] {
        let valRes = err.json() as ValidationResult;
        if (valRes.message.startsWith('This')) {
            return [valRes.message];
        }
        let items = valRes.items;
        let messages: string[] = [];
        items.map(item => {
            item.messages.map(message => {
                messages.push(message);
            });
        });
        return messages;
    }

    showError(field: string): boolean {
        return !this.userForm.controls[field].valid && !(this.userForm.controls[field].value === '');
    }

}
