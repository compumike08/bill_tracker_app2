'use strict';

angular.module('billTrackerApp2App')
    .factory('Bill', function ($resource, DateUtils) {
        return $resource('api/bills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.bill_statement_date = DateUtils.convertLocaleDateFromServer(data.bill_statement_date);
                    data.bill_due_date = DateUtils.convertLocaleDateFromServer(data.bill_due_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.bill_statement_date = DateUtils.convertLocaleDateToServer(data.bill_statement_date);
                    data.bill_due_date = DateUtils.convertLocaleDateToServer(data.bill_due_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.bill_statement_date = DateUtils.convertLocaleDateToServer(data.bill_statement_date);
                    data.bill_due_date = DateUtils.convertLocaleDateToServer(data.bill_due_date);
                    return angular.toJson(data);
                }
            }
        });
    });
