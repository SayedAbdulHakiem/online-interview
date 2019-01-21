/*global $, alert, console */

$(function () {
   
    'use strict';

    var userError   = true,
        
        emailError  = true,
        
        passError = true,
        
        addressError= true,
        
        posError = true,
        
        numError = true;
    
    $('.username').blur(function () {
       
        if ($(this).val().length < 4 || $(this).val() === '') { // Show Error
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure user name is more than 4 characters");
            
            userError = true;
            
        } else { 
                        
            $(this).css('border', '4px solid #080');
            
            userError = false;
            
        }
        
    });
    
    $('.email').blur(function () {
       
        if ($(this).val() === '') {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure email is exist");  
            emailError = true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
            emailError = false;
            
        }
        
    });
    
    $('.pass').blur(function () {
       
        if ($(this).val() === '' || $(this).val().length <4) {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure password is exist or more than 4 characters");  
           passError = true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
           passError = false;
            
        }
        
    });
    $('.pos').blur(function () {
       
        if ($(this).val() === '') {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure to write your position");  
           posError = true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
           posError = false;
            
        }
        
    });
    $('.add').blur(function () {
       
        if ($(this).val() === '') {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure to write your address");  
            addressError= true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
           addressError= false;
            
        }
        
    });
    
     $('.num').blur(function () {
       
        if ($(this).val() === '') {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure to write your number");  
            numError = true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
            numError = false;
            
        }
        
    });
    
    
 var msg = $('.msg').text();
 
 
     
 
   
    
    $('.form').submit(function (e) {
        
        if (msg !== '' || userError === true || emailError === true || passError === true || addressError === true || posError=== true || numError===true) {
            
            e.preventDefault();
            
            $('.username, .email, .pass, .pos , .add, .num').blur();
        
            
        }
        
    });
    
});