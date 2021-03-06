angular.module('dataSource', ['ngRoute', 'ngResource', 'controllers', 'localytics.directives', 'directives' ]).config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl : './html/pages/dataSourceChart.html',
                controller  : 'dataSourceChartController'
            })
            .when('/dataSourceChart', {
            	templateUrl : './html/pages/dataSourceChart.html',
            	controller  : 'dataSourceChartController'
            })
            .when('/correlationChart', {
            	templateUrl : './html/pages/correlationChart.html',
            	controller  : 'correlationChartController'
            })
            .when('/outlineChart', {
            	templateUrl : './html/pages/outlineChart.html',
            	controller  : 'outlineChartController'
            })
    });