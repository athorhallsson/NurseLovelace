$(document).ready(function() {
            $("#question").hide();
            $("#confirm").hide();
           // $("#personal-info").hide();

           var availableTags = [
                "ActionScript",
                "Fortran",
                "Groovy",
                "Haskell",
                "Java",
                "JavaScript",
                "Lisp",
            ];
             $( "#tags" ).autocomplete({
                source: availableTags
             });
            
            var ageSelect = $("#age" );
            for (i = 0; i < 120; i++) {
                ageSelect.append('<option value="' + i + '">' + i + '</option>');
            }
            

            var initForm = $("#initform");
            initForm.submit(function(event) {
                var age = $("#age").val();
                var gender = $("#gender").val();
                $.ajax({
                    type: initForm.attr('method'),
                    url: initForm.attr('action'),
                    data: 'age=' + age + '&gender=' + gender
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

            var confirmForm = $("#confirmform");
            confirmForm.submit(function(event) {
                var answer = "true";
                $.ajax({
                    type: confirmForm.attr('method'),
                    url: confirmForm.attr('action'),
                    data: 'answer=' + answer
                }).done(function() {
                    $("#results").html('Thank you.').attr('class', 'alert alert-success');
                    $("#confirm").hide();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
                event.preventDefault();
            });

            var questionForm = $("#questionform");
            questionForm.submit(function(event) {
                var answer = "true";
                var symptom = $("#symptomid").val();
                $.ajax({
                    type: questionForm.attr('method'),
                    url: questionForm.attr('action'),
                    data: 'answer=' + answer + '&symptom=' + symptom,
                    success : function(text)
                    {
                        var obj = JSON.parse(text); 
                        $("#symptomid").attr("value", obj.symptom);
                    }
                }).done(function() {
                    $("#results").html('Symptom number ' + symptom + ' confirmed.').attr('class', 'alert alert-success');
                    $("#confirm").hide();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
                event.preventDefault();
            });


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