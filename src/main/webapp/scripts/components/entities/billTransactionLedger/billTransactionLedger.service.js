'use strict';

angular.module('billTrackerApp2App')
    .factory('BillTransactionLedger', function ($resource, DateUtils) {
        return $resource('api/billTransactionLedgers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.bill_trsctn_datetime = DateUtils.convertDateTimeFromServer(data.bill_trsctn_datetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
