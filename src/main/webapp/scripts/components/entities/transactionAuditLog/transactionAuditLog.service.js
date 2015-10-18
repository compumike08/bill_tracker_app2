'use strict';

angular.module('billTrackerApp2App')
    .factory('TransactionAuditLog', function ($resource, DateUtils) {
        return $resource('api/transactionAuditLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.trsctn_datetime = DateUtils.convertDateTimeFromServer(data.trsctn_datetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
