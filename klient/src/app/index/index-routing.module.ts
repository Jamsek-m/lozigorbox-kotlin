import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IndexComponent} from "./index/index.component";
import {FileListComponent} from "./file-list/file-list.component";

const routes: Routes = [
    {
        path: "", component: IndexComponent, pathMatch: "full", children: [
            {path: "", component: FileListComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class IndexRoutingModule {

}
