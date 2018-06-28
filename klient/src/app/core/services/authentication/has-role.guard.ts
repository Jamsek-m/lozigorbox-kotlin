import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {AuthenticationService} from "./authentication.service";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";
import {VlogaTypes} from "../../../entities/models/uporabnik.model";

@Injectable()
export class HasRoleGuard implements CanActivate {

    constructor(private auth: AuthenticationService, private router: Router) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        const vloge: VlogaTypes[] = route.data["roles"];
        return this.auth.imaEnoOdVlog(vloge).pipe(map((aktivna: boolean) => {
            if (!aktivna) {
                this.router.navigate(["403"]);
                return false;
            }
            return true;
        }));
    }

}

export class VlogaGuardData {

    public roles: VlogaTypes[];

    constructor(vloge: VlogaTypes[]) {
        this.roles = vloge;
    }
}
