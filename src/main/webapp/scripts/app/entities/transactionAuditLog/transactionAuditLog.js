'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('transactionAuditLog', {
                parent: 'entity',
                url: '/transactionAuditLogs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TransactionAuditLogs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transactionAuditLog/transactionAuditLogs.html',
                        controller: 'TransactionAuditLogController'
                    }
                },
                resolve: {
                }
            })
            .state('transactionAuditLog.detail', {
                parent: 'entity',
                url: '/transactionAuditLog/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TransactionAuditLog'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transactionAuditLog/transactionAuditLog-detail.html',
                        controller: 'TransactionAuditLogDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TransactionAuditLog', function($stateParams, TransactionAuditLog) {
                        return TransactionAuditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('transactionAuditLog.new', {
                parent: 'transactionAuditLog',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transactionAuditLog/transactionAuditLog-dialog.html',
                        controller: 'TransactionAuditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {debit_amount: null, credit_amount: null, trsctn_datetime: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('transactionAuditLog', null, { reload: true });
                    }, function() {
                        $state.go('transactionAuditLog');
                    })
                }]
            })
            .state('transactionAuditLog.edit', {
                parent: 'transactionAuditLog',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transactionAuditLog/transactionAuditLog-dialog.html',
                        controller: 'TransactionAuditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TransactionAuditLog', function(TransactionAuditLog) {
                                return TransactionAuditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transactionAuditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
