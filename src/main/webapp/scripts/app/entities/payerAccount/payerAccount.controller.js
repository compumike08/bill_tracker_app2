'use strict';

angular.module('billTrackerApp2App')
    .controller('PayerAccountController', function ($scope, PayerAccount) {
        $scope.payerAccounts = [];
        $scope.loadAll = function() {
            PayerAccount.query(function(result) {
               $scope.payerAccounts = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PayerAccount.get({id: id}, function(result) {
                $scope.payerAccount = result;
                $('#deletePayerAccountConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PayerAccount.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePayerAccountConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.payerAccount = {payer_acct_name: null, id: null};
        };
    });
