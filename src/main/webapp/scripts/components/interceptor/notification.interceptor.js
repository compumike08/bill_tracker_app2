 'use strict';

angular.module('billTrackerApp2App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-billTrackerApp2App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-billTrackerApp2App-params')});
                }
                return response;
            },
        };
    });