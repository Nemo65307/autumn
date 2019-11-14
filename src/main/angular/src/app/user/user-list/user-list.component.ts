import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {UserService} from '../user.service';
import {User} from '../User';
import {Router} from '@angular/router';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.css'],
    providers: [UserService]
})

export class UserListComponent implements OnInit {

    users: User[];

    constructor(private router: Router, private userService: UserService) {
    }

    ngOnInit() {
        this.getAllUsers();
    }

    getAllUsers() {
        this.userService.findAll().subscribe(
            users => {
                this.users = users;
            },
            err => {
                console.log(err);
            }
        );
    }

    redirectNewUserPage() {
        this.router.navigate(['/admin/add-user'], { skipLocationChange: true });
    }

    editUserPage(user: User) {
        if (user) {
            this.router.navigate(['/admin/edit-user', user.id], { skipLocationChange: true });
        }
    }

    deleteUser(user: User) {
        if (window.confirm('Are you sure you want to delete this item?')) {
            if (user) {
                this.userService.deleteUserById(user.id).subscribe(
                    () => {
                        this.getAllUsers();
                        this.router.navigate(['/admin/users-list'], { skipLocationChange: true });
                    }
                );
            }
        }
    }

    getAge(dateString: string) {
        const today = new Date();
        const birthDate = new Date(dateString);
        const m = today.getMonth() - birthDate.getMonth();
        let age = today.getFullYear() - birthDate.getFullYear();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age;
    }


}
