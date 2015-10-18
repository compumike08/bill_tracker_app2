'use strict';

angular.module('billTrackerApp2App')
    .controller('PayerTransactionLedgerDetailController', function ($scope, $rootScope, $stateParams, entity, PayerTransactionLedger, PayerAccount) {
        $scope.payerTransactionLedger = entity;
        $scope.load = function (id) {
            PayerTransactionLedger.get({id: id}, function(result) {
                $scope.payerTransactionLedger = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:payerTransactionLedgerUpdate', function(event, result) {
            $scope.payerTransactionLedger = result;
        });
    });
