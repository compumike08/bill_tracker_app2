'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('billAmountsOwed', {
                parent: 'entity',
                url: '/billAmountsOweds',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BillAmountsOweds'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billAmountsOwed/billAmountsOweds.html',
                        controller: 'BillAmountsOwedController'
                    }
                },
                resolve: {
                }
            })
            .state('billAmountsOwed.detail', {
                parent: 'entity',
                url: '/billAmountsOwed/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BillAmountsOwed'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billAmountsOwed/billAmountsOwed-detail.html',
                        controller: 'BillAmountsOwedDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'BillAmountsOwed', function($stateParams, BillAmountsOwed) {
                        return BillAmountsOwed.get({id : $stateParams.id});
                    }]
                }
            })
            .state('billAmountsOwed.new', {
                parent: 'billAmountsOwed',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/billAmountsOwed/billAmountsOwed-dialog.html',
                        controller: 'BillAmountsOwedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {amount_owed: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('billAmountsOwed', null, { reload: true });
                    }, function() {
                        $state.go('billAmountsOwed');
                    })
                }]
            })
            .state('billAmountsOwed.edit', {
                parent: 'billAmountsOwed',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/billAmountsOwed/billAmountsOwed-dialog.html',
                        controller: 'BillAmountsOwedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BillAmountsOwed', function(BillAmountsOwed) {
                                return BillAmountsOwed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('billAmountsOwed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
