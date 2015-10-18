'use strict';

angular.module('billTrackerApp2App')
    .factory('PayerTransactionLedger', function ($resource, DateUtils) {
        return $resource('api/payerTransactionLedgers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.payer_trsctn_datetime = DateUtils.convertDateTimeFromServer(data.payer_trsctn_datetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
