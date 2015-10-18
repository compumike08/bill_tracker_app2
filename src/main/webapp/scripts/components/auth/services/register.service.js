'use strict';

angular.module('billTrackerApp2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


