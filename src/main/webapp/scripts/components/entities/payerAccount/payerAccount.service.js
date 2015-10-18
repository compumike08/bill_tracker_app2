'use strict';

angular.module('billTrackerApp2App')
    .factory('PayerAccount', function ($resource, DateUtils) {
        return $resource('api/payerAccounts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
