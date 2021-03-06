import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AdminRoutingModule} from "./admin-routing.module";
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        AdminRoutingModule,
        SharedModule
    ],
    declarations: [
        DashboardComponent
    ]
})
export class AdminModule {
}
