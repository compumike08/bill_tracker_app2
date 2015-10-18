'use strict';

angular.module('billTrackerApp2App')
    .controller('BillTransactionLedgerDetailController', function ($scope, $rootScope, $stateParams, entity, BillTransactionLedger, Bill) {
        $scope.billTransactionLedger = entity;
        $scope.load = function (id) {
            BillTransactionLedger.get({id: id}, function(result) {
                $scope.billTransactionLedger = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:billTransactionLedgerUpdate', function(event, result) {
            $scope.billTransactionLedger = result;
        });
    });
