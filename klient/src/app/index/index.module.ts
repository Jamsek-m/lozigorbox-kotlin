import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {IndexComponent} from "./index/index.component";
import {FileListComponent} from "./file-list/file-list.component";
import {IndexRoutingModule} from "./index-routing.module";
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        IndexRoutingModule,
        SharedModule
    ],
    declarations: [
        IndexComponent,
        FileListComponent
    ]
})
export class IndexModule {
}
