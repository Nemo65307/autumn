import {Role} from './Role';

export class User {

    id: string;
    login: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    birthday: string;
    role: Role;

    constructor(id: string, login: string, email: string, password: string, firstName: string, lastName: string, birthday: string, role: Role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }

}
