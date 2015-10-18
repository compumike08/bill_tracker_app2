'use strict';

angular.module('billTrackerApp2App')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
