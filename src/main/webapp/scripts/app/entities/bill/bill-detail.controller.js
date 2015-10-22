'use strict';

angular.module('billTrackerApp2App')
    .controller('BillDetailController', function ($scope, $rootScope, $stateParams, entity, Bill, BillAmountsOwed, BillAmountsOwedByBill, BillTransactionLedger) {
        $scope.bill = entity;
        $scope.load = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
            });

            BillAmountsOwedByBill.query({id: id}, function(result){
                $scope.bill.billAmountsOwed = result;
            });
        };

        $scope.load($scope.bill.id);

        $rootScope.$on('billTrackerApp2App:billUpdate', function(event, result) {
            $scope.bill = result;
        });
    });
