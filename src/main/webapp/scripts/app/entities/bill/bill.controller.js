'use strict';

angular.module('billTrackerApp2App')
    .controller('BillController', function ($scope, Bill) {
        $scope.bills = [];
        $scope.loadAll = function() {
            Bill.query(function(result) {
               $scope.bills = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
                $('#deleteBillConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bill.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBillConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bill = {bill_name: null, bill_amount: null, bill_statement_date: null, bill_due_date: null, is_bill_paid: null, is_bill_reimbursed: null, id: null};
        };
    });
