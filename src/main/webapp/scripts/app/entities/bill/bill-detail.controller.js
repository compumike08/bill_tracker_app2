'use strict';

angular.module('billTrackerApp2App')
    .controller('BillDetailController', function ($scope, $rootScope, $stateParams, entity, Bill, BillAmountsOwed, BillTransactionLedger) {
        $scope.bill = entity;
        $scope.load = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:billUpdate', function(event, result) {
            $scope.bill = result;
        });
    });
