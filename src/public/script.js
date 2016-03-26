$(document).ready(function() {
            $("#question").hide();
            $("#confirm").hide();
            $("#correct-diagnosis").hide();
            $("#pain").hide();

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
                    $("#tags").autocomplete({
                        source: symptoms
                    });
                }
            }).fail(function() {
                $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
            });

            // Age dropdown list
            var ageSelect = $("#age" );
            for (i = 0; i < 120; i++) {
                ageSelect.append('<option value="' + i + '">' + i + '</option>');
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
            var initForm = $("#initform");
            initForm.submit(function(event) {
                var painString = $("#pain-description").val() + "_" + $("#pain-when").val() + "_"
                painString += $("#pain-long").val() + "_" + $("#pain-changes").val();
                var pos =  $("#pain-position").val();
                var rpos = $("#pain-rposition").val();
                var age = $("#age").val();
                var gender = $("#gender").val();
                var mainSymptom = symptoms.indexOf($("#tags").val());
                $.ajax({
                    type: initForm.attr('method'),
                    url: initForm.attr('action'),
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
                event.preventDefault();
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
                confirmCallAction("true");
            });

            $("#confirm-false").on('click', function(e) {
                $("#confirm").hide();
                $("#correct-diagnosis").show();
            });

            $("#correct-diagnosis-btn").on('click', function(e) {
                var newDiagnosis = $("#correct-diagnosis-text").val();
                confirmCallAction(newDiagnosis);
                $("#correct-diagnosis").hide();
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
                        $("#symptomid").attr("value", symptomIndex);
                        $("#symptom").html(symptoms[symptomIndex])
                    }
                }).done(function() {
                    $("#results").html('Symptom number ' + symptom + ' marked as ' + answer + '.').attr('class', 'alert alert-success');
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
            var doneBtn = $("#done-button");
            doneBtn.click(function(event) {
                $.ajax({
                    type: "get",
                    url: '/done',
                    data: null,
                    success : function(text)
                    {
                        var obj = JSON.parse(text); 
                        $("#diagnosis").html(obj.diagnosis);
                    }
                }).done(function() {
                    $("#results").html('We really hope it is.').attr('class', 'alert alert-success');
                    $("#question").hide();
                    $("#confirm").show();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
            });


        });