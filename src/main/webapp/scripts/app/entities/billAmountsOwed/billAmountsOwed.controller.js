'use strict';

angular.module('billTrackerApp2App')
    .controller('BillAmountsOwedController', function ($scope, BillAmountsOwed) {
        $scope.billAmountsOweds = [];
        $scope.loadAll = function() {
            BillAmountsOwed.query(function(result) {
               $scope.billAmountsOweds = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            BillAmountsOwed.get({id: id}, function(result) {
                $scope.billAmountsOwed = result;
                $('#deleteBillAmountsOwedConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BillAmountsOwed.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBillAmountsOwedConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.billAmountsOwed = {amount_owed: null, id: null};
        };
    });
