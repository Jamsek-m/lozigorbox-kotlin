import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {Napaka404Component} from "./napake/404/napaka404.component";
import {Napaka403Component} from "./napake/403/napaka403.component";

const routes: Routes = [
    {path: "", pathMatch: "full", loadChildren: "../index/index.module#IndexModule"},
    {path: "admin", loadChildren: "../admin/admin.module#AdminModule", canActivate: []},
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
