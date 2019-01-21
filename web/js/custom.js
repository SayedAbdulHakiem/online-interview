/*global $, alert, console */

$(function () {
   
    'use strict';

    var userError   = true,
        
        emailError  = true,
        
        msgError    = true;
    
    $('.email').blur(function () {
       
        if ($(this).val().length < 4 || $(this).val() === '') { // Show Error
                        
            $(this).css('border', '4px solid #F00');
             alert("make sure mail is true");
            
            emailError  = true;
            
        } else { // No Errors
                        
            $(this).css('border', '4px solid #080');
           
            
            emailError = false;
            
        }
        
    });
    
    $('.pass').blur(function () {
       
        if ($(this).val() === '' || $(this).val().length < 4) {
                        
            $(this).css('border', '4px solid #F00');
            alert("make sure password is exist or more than 4 character");
           userError = true;
            
        } else {
                        
            $(this).css('border', '4px solid #080');
                        
           
            userError = false;
            
            
        }
        
    });
    
    
   
    // Submit Form Validation
    
    $('.form').submit(function (e) {
        
        if (userError === true || emailError === true) {
            
            e.preventDefault();
            
            $('.email, .pass').blur();
            
        }
        
    });
    
});