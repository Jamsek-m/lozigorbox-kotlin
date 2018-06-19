import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {IndexComponent} from "./index/index.component";
import {FileListComponent} from "./file-list/file-list.component";
import {IndexRoutingModule} from "./index-routing.module";

@NgModule({
    imports: [
        CommonModule,
        IndexRoutingModule
    ],
    declarations: [
        IndexComponent,
        FileListComponent
    ]
})
export class IndexModule {
}
