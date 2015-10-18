'use strict';

angular.module('billTrackerApp2App')
    .factory('BillAmountsOwed', function ($resource, DateUtils) {
        return $resource('api/billAmountsOweds/:id', {}, {
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
