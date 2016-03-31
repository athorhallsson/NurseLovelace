$(document).ready(function() {
            $("#question").hide();
            $("#confirm").hide();
            $("#correct-diagnosis").hide();
            $("#pain").hide();
            $("#add-more").hide();

            var symptoms;
            // Autocomplete symptoms
            $.ajax({
                type: "get",
                url: "/symptoms",
                data: null, 
                success : function(text)
                {
                    var obj = JSON.parse(text); 
                    symptoms = obj.symptoms;
                    $("#main-symptom").autocomplete({
                        source: symptoms
                    });
                    $("#add-more-box").autocomplete({
                        source: symptoms
                    });
                }
            }).fail(function() {
                $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
            });

            // Age dropdown list
            var ageSelect = $("#age" );
            for (i = 0; i < 120; i++) {
                if (i == 40) {
                    ageSelect.append('<option value="' + i + '" selected>' + i + '</option>');
                }
                else {
                    ageSelect.append('<option value="' + i + '">' + i + '</option>');
                }
            }

            // Pain lists
            $("#has-pain").change(function () {
                if ($("#has-pain").prop("checked")) {
                    $("#pain").show();
                }
                else {
                    $("#pain").hide();
                }   
            });  

            // INIT
            $("#initform-btn").on('click', function(e) {
                if (symptoms.indexOf($("#main-symptom").val()) == -1) {
                    $('#results').html('Please enter a valid symptom.').attr('class', 'alert alert-danger');
                    return;
                }

                var painString = $("#pain-description").val() + "_" + $("#pain-when").val() + "_";
                painString += $("#pain-long").val() + "_" + $("#pain-changes").val();
                var pos =  $("#pain-position").val();
                var rpos = $("#pain-rposition").val();
                var age = $("#age").val();
                var gender = $("#gender").val();
                var mainSymptom = symptoms.indexOf($("#main-symptom").val());
                $.ajax({
                    type: "post",
                    url: "/initinfo",
                    data: 'age=' + age + '&gender=' + gender + '&mainsymptom=' + mainSymptom + '&pain=' + painString + '&pos=' + pos + '&rpos=' + rpos,
                    success : function(text)
                    {
                        var obj = JSON.parse(text); 
                        var symptomIndex = obj.symptom;
                        $("#symptomid").attr("value", symptomIndex);
                        $("#symptom").html(symptoms[symptomIndex])
                    }
                }).done(function() {
                    $("#results").html('Age set as ' + age + ' and gender set as ' +
                        gender + '.').attr('class', 'alert alert-success');
                    $("#personal-info").hide();
                    $("#confirm").hide();
                    $("#question").show();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            });

            // CONFIRM
            function confirmCallAction(action) {
                var answer = action;
                $.ajax({
                    type: "post",
                    url: "/confirm",
                    data: 'answer=' + answer
                }).done(function() {
                    $("#results").html('Thank you.').attr('class', 'alert alert-success');
                    $("#confirm").hide();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            };

            $("#confirm-true").on('click', function(e) {
                var checkboxValue = $("#hidden-input").val();

                if (checkboxValue == "none-of-the-above") {
                    $("#confirm").hide();
                    getAllSymptoms();
                }
                else {
                    confirmCallAction(checkboxValue);
                }
            });

            $("#correct-diagnosis-btn").on('click', function(e) {
                var newDiagnosis = $("#correct-diagnosis-text").val();
                var majorSxJson = $("#hidden-majorSx").val();
                addNewDx(majorSxJson, newDiagnosis);
            });

            // QUESTION
            function questionCallAction(action) {
                var answer = action;
                var symptom = $("#symptomid").val();
                $.ajax({
                    type: "post",
                    url: "/answer",
                    data: 'answer=' + answer + '&symptom=' + symptom,
                    success : function(text)
                    {
                        var obj = JSON.parse(text); 
                        var symptomIndex = obj.symptom;
                        if (symptomIndex == -1) {
                            $("#question").hide();
                            $("#add-more").show();
                        }
                        $("#symptomid").attr("value", symptomIndex);
                        $("#symptom").html(symptoms[symptomIndex])
                    }
                }).done(function() {
                    $("#results").html('Symptom ' + symptoms[symptom] + ' marked as ' + answer + '.').attr('class', 'alert alert-success');
                    $("#confirm").hide();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            };

            $("#question-true").on('click', function(e) {
                questionCallAction("true");
            });

            $("#question-false").on("click", function(e) {
                questionCallAction("false");
            });

            // DONE
            var doneBtn = $("#done-button, #add-more-btn-no");
            doneBtn.click(function(event) {
                $.ajax({
                    type: "get",
                    url: '/done',
                    data: null,
                    success : function(text)
                    {
                        $("#question").hide();
                        $("#add-more").hide();
                        var obj = JSON.parse(text);
                        if (obj.length == 0) {
                            getAllSymptoms();
                            $("#results").html("Please have your doctor check the correct diagnosis and press proceed").attr('class', 'alert alert-success');
                            $("#correct-diagnosis").show();
                            return;
                        }
                        else {
                            $("#confirm").show();
                            $("#results").html('Please have your doctor check the correct diagnosis and press proceed').attr('class', 'alert alert-success');
                        }
                        for (var ddx in obj) {
                            $("#diagnosis").append('<p><input type="checkbox" name="' + obj[ddx] + '" id="' + obj[ddx] + '" value="' + obj[ddx] + '"/><label for="' + obj[ddx] + '">' + obj[ddx] + '</label></p>');
                        }
                        $("input").on("click", function() {
                             $("#hidden-input").val($(this).val());
                        })
                    }
                }).done(function() {
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            });

            // ADD MORE
            var addMoreBtn = $("#add-more-btn-yes");
            addMoreBtn.click(function(event) {
                if (symptoms.indexOf($("#add-more-box").val()) == -1) {
                    $('#results').html('Please enter a valid symptom.').attr('class', 'alert alert-danger');
                    return;
                }
                var answer = "true";
                var symptom = symptoms.indexOf($("#add-more-box").val());
                $.ajax({
                    type: "post",
                    url: "/answer",
                    data: 'answer=' + answer + '&symptom=' + symptom,
                    success : function(text)
                    {
                        var obj = JSON.parse(text); 
                        var symptomIndex = obj.symptom;
                        $("#symptomid").attr("value", symptomIndex);
                        $("#symptom").html(symptoms[symptomIndex])
                    }
                }).done(function() {
                    $("#add-more-box").val("");
                    $("#add-more").hide();
                    $("#question").show();
                    $("#results").html('Symptom number ' + symptom + ' marked as ' + answer + '.').attr('class', 'alert alert-success');
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            });

            function getAllSymptoms(){
                $.ajax({
                    type: "get",
                    url: "/getSymptomsForCase",
                    success : function(text)
                    {
                        var obj = JSON.parse(text)
                        for (var ddx in obj) {
                            $("#sxList").append('<p><input type="checkbox" name="' + symptoms[obj[ddx]] + '" id="' + symptoms[obj[ddx]] + '" value="' + obj[ddx] + '"/><label for="' + symptoms[obj[ddx]] + '">' + '  ' + symptoms[obj[ddx]] + '</label></p>');
                        }
                        $("input").on("click", function() {
                             var prevValue = $("#hidden-majorSx").val();
                             $("#hidden-majorSx").val(prevValue + $(this).val() + ',');
                        })
                    }
                 }).done(function() {
                     $("#results").html("Please have your doctor check the major symptoms, enter a diagnosis and press send").attr('class', 'alert alert-success');
                     $("#correct-diagnosis").show();
                 }).fail(function() {
                     $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                 });
            };

            function addNewDx(majorSx, newDx){
                $.ajax({
                    type: "post",
                    url: "/addNewDiagnosis",
                    data: 'majorSx=' + majorSx + '&newDx=' + newDx
                 }).done(function() {
                     $("#results").html("Thank you").attr('class', 'alert alert-success');
                     $("#correct-diagnosis").hide();
                 }).fail(function() {
                     $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                 });
            };

            // RESET
            var resetBtn = $("#title");
            resetBtn.click(function(event) {
                $.ajax({
                    type: "get",
                    url: "/reset"
                 }).done(function() {
                     location.reload();
                 }).fail(function() {
                     $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                 });
            });

        });