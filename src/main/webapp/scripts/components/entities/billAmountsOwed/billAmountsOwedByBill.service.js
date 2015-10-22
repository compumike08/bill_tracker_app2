'use strict';

angular.module('billTrackerApp2App')
    .factory('BillAmountsOwedByBill', function ($resource, DateUtils) {
        return $resource('api/billAmountsOweds/bills/:id', {}, {
            'query': {
                method: 'GET',
                isArray: true
            }
        });
    });
