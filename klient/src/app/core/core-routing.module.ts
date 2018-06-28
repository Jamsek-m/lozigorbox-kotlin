import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {Napaka404Component} from "./napake/404/napaka404.component";
import {Napaka403Component} from "./napake/403/napaka403.component";
import {LoginComponent} from "./login/login.component";
import {AuthenticatedGuard} from "./services/authentication/authenticated.guard";
import {VlogaTypes} from "../entities/models/uporabnik.model";
import {HasRoleGuard, VlogaGuardData} from "./services/authentication/has-role.guard";

const routes: Routes = [
    {path: "", pathMatch: "full", loadChildren: "../index/index.module#IndexModule",
        canActivate: [AuthenticatedGuard]},
    {path: "admin", loadChildren: "../admin/admin.module#AdminModule",
        canActivate: [HasRoleGuard], data: new VlogaGuardData([VlogaTypes.ADMIN, VlogaTypes.MOD])},
    {path: "login", component: LoginComponent},
    {path: "404", component: Napaka404Component},
    {path: "403", component: Napaka403Component},
    {path: "**", redirectTo: "/404"}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class CoreRoutingModule {

}
