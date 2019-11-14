import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserListComponent} from './user-list/user-list.component';
import {UserFormComponent} from './user-form/user-form.component';

const routes: Routes = [
    {path: 'admin/users-list', component: UserListComponent},
    {path: 'admin/add-user', component: UserFormComponent},
    {path: 'admin/edit-user/:id', component: UserFormComponent}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserRoutingModule {
}
