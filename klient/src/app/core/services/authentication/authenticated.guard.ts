import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {AuthenticationService} from "./authentication.service";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthenticatedGuard implements CanActivate {

    constructor(private auth: AuthenticationService, private router: Router) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return this.auth.prijavaJeAktivna().pipe(map((aktivna: boolean) => {
            if (!aktivna) {
                this.router.navigate(["login"], {queryParams: {return: state.url}});
                return false;
            }
            return true;
        }));
    }

}
