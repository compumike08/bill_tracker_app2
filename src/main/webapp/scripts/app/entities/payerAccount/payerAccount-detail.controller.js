'use strict';

angular.module('billTrackerApp2App')
    .controller('PayerAccountDetailController', function ($scope, $rootScope, $stateParams, entity, PayerAccount, TransactionAuditLog, PayerTransactionLedger, BillAmountsOwed) {
        $scope.payerAccount = entity;
        $scope.load = function (id) {
            PayerAccount.get({id: id}, function(result) {
                $scope.payerAccount = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:payerAccountUpdate', function(event, result) {
            $scope.payerAccount = result;
        });
    });
