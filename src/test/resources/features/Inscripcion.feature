Feature: Inscripción a cursos
  El estudiante I2024001 ya está inscrito en el curso 1

  Scenario: Inscripción correcta
    Given un estudiante nuevo con un código distinto en cada ejecución
    When el estudiante se inscribe en el curso 2
    Then el sistema responde con el código 200

  Scenario: Inscripción duplicada
    Given el estudiante "I2024001" ya está inscrito en el curso 1
    When el estudiante intenta inscribirse de nuevo en el curso 1
    Then el sistema rechaza la inscripción con un código de error 4xx o 5xx
