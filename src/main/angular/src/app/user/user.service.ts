import {Injectable} from '@angular/core';
import {User} from './User';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs-compat/add/operator/map';
import 'rxjs-compat/add/operator/catch';
import {Role} from './Role';

@Injectable()
export class UserService {

    private apiUrl = '/api/rest';

    constructor(private http: Http) {
    }

    findAll(): Observable<User[]> {
        return this.http.get(this.apiUrl + '/user/all')
            .map((res: Response) => res.json());
    }

    findById(id: string): Observable<User> {
        return this.http.get(this.apiUrl + '/user/' + id)
            .map((res: Response) => res.json());
    }

    updateUser(user: User, id: string): Observable<Response> {
        return this.http.put(this.apiUrl + '/user/' + id, user);
    }

    saveUser(user: User): Observable<Response> {
        return this.http.post(this.apiUrl + '/user', user);
    }

    deleteUserById(id: string): Observable<Response> {
        return this.http.delete(this.apiUrl + '/user/' + id);
    }

    findAllRoles(): Observable<Role[]> {
        return this.http.get(this.apiUrl + '/role/all')
            .map((res: Response) => res.json());
    }

}
