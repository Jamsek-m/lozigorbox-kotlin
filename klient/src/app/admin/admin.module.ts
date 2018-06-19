import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AdminRoutingModule} from "./admin-routing.module";

@NgModule({
    imports: [
        CommonModule,
        AdminRoutingModule
    ],
    declarations: [
        DashboardComponent
    ]
})
export class AdminModule {
}
