/* tslint:disable */
import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../Services/auth.service';
import {TokenService} from '../Services/token.service';
import {Router} from '@angular/router';
//import {$} from 'jquery';
import * as $ from "jquery";
import {EnseignantService} from '../Services/enseignant.service';
import {MatSnackBar} from '@angular/material/snack-bar';



@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required,Validators.email]),
    password: new FormControl(null, [Validators.required]),
  })
  constructor(private authService: AuthService,
              private tokenService: TokenService,
              private route: Router,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {


    this.tokenService.remove();
  }
  login(){
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(res => {
        this.handleResponse(res);
        this.redirectTo();
        if (res == null) {
          this.snackBar.open("le login ou le mot de passe est invalide  ", "", {
            duration: 3000,
            verticalPosition: 'top',
            panelClass: ['aa']
          });
        } else {
          this.snackBar.open("Authentification avec succès    ", "", {
            duration: 3000,
            verticalPosition: 'top',
            panelClass: ['bb']
          });
        }

      }, error => {
        this.snackBar.open("Votre email ou mot de pass  est invalide  ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
      })
    }else{
      this.validateAllFormFields(this.loginForm);
    }
  }
  redirectTo(){

    switch (this.tokenService.getFonction()){
      case "Enseignant": {
         this.route.navigate(['/']);
        break;
      }
      case "Directeur":{
        this.route.navigate(['/']);
        break;
      }
      case "Directeur de departement":{
        this.route.navigate(['/']);
        break;
      }
      case "directeur visiteur":{
        this.route.navigate(['/']);
        break;
      }
      case "ressource humaine":{
        this.route.navigate(['/']);
      }

    }
  }
  handleResponse(res){
      this.tokenService.handel(res)
    setTimeout(() => this.activate(), 500);

  }
  toRegister(){
    this.route.navigate(['/register']);
  }
  validateAllFormFields(formGroup: FormGroup)
  {Object.keys(formGroup.controls).forEach(field =>
  {const control = formGroup.get(field);
    if (control instanceof FormControl)
    {control.markAsTouched({ onlySelf: true }); }
    else if (control instanceof FormGroup)
    {this.validateAllFormFields(control); }});
  }
get email(){
    return this.loginForm.get('email');
}
get password(){
    return this.loginForm.get('password');
}
  activate() {


    "use strict"; // Start of use strict

    // Toggle the side navigation
    // tslint:disable-next-line:only-arrow-functions
    $("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
      $("body").toggleClass("sidebar-toggled");
      $(".sidebar").toggleClass("toggled");
      if ($(".sidebar").hasClass("toggled")) {
        $('.sidebar .collapse').collapse('hide');
      }
    });

    // Close any open menu accordions when window is resized below 768px
    // tslint:disable-next-line:only-arrow-functions
    $(window).resize(function() {
      if ($(window).width() < 768) {
        $('.sidebar .collapse').collapse('hide');
      }

      // Toggle the side navigation when window is resized below 480px
      if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
        $("body").addClass("sidebar-toggled");
        $(".sidebar").addClass("toggled");
        $('.sidebar .collapse').collapse('hide');
      }
    });


  }

}
