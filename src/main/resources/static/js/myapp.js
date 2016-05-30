var myapp = angular.module("myapp", []);
myapp.controller("InscriptionContoller", function($scope, $http) {
	$scope.etudiant = {};
	$scope.mode = {
		value : "form"
	};
	$scope.pageEtudiants = null;
	$scope.errors = null;
	$scope.pageCourante = 0;
	$scope.size = 5;
	$scope.saveEtudiant = function() {
		$http.post("etudiants", $scope.etudiant).success(function(data) {
			if (!data.errors) {
				$scope.mode.value = "confirm";
				$scope.etudiant = data;
				$scope.errors = null;

			} else {
				$scope.errors = data;
				$scope.etudiant = null;
			}

		});

	};
	$scope.listEtudiants = function() {
		$http.get(
				"etudiants?page=" + $scope.pageCourante + "&size="
						+ $scope.size).success(function(data) {
			$scope.pageEtudiants = data;

		})

	};
	$scope.listEtudiants();

});