'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bill', {
                parent: 'entity',
                url: '/bills',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bills'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bills.html',
                        controller: 'BillController'
                    }
                },
                resolve: {
                }
            })
            .state('bill.detail', {
                parent: 'entity',
                url: '/bill/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bill'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bill-detail.html',
                        controller: 'BillDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bill', function($stateParams, Bill) {
                        return Bill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bill.new', {
                parent: 'bill',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {bill_name: null, bill_amount: null, bill_statement_date: null, bill_due_date: null, is_bill_paid: null, is_bill_reimbursed: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('bill');
                    })
                }]
            })
            .state('bill.edit', {
                parent: 'bill',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
