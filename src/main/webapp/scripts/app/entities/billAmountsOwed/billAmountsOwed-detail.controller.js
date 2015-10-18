'use strict';

angular.module('billTrackerApp2App')
    .controller('BillAmountsOwedDetailController', function ($scope, $rootScope, $stateParams, entity, BillAmountsOwed, PayerAccount, Bill) {
        $scope.billAmountsOwed = entity;
        $scope.load = function (id) {
            BillAmountsOwed.get({id: id}, function(result) {
                $scope.billAmountsOwed = result;
            });
        };
        $rootScope.$on('billTrackerApp2App:billAmountsOwedUpdate', function(event, result) {
            $scope.billAmountsOwed = result;
        });
    });
