'use strict';

angular.module('billTrackerApp2App')
    .controller('TransactionAuditLogDetailController', function ($scope, $rootScope, $stateParams, entity, TransactionAuditLog, PayerAccount) {
        $scope.transactionAuditLog = entity;
        $scope.load = function (id) {
            TransactionAuditLog.get({id: id}, function(result) {
                $scope.transactionAuditLog = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:transactionAuditLogUpdate', function(event, result) {
            $scope.transactionAuditLog = result;
        });
    });
