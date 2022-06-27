

/// for transaction file upload

var $f1, $f2;

$(document).ready(function() {
	$('#testForm').on('submit', processForm);
	$f1 = $('#file1');
	$f2 = $('#file2');

});

function processForm(e) {
	e.preventDefault();
	console.log('processForm');

	var formData = new FormData();

	if($f1.val()) formData.append('file1', $f1.get(0).files[0]);
	if($f2.val()) formData.append('file2', $f2.get(0).files[0]);

	var request = new XMLHttpRequest();
	request.open('POST', 'api/v1/uploadCsvFiles');
	request.send(formData);

	request.onload = function(e) {

		console.log('Request Status', request.status,e);
	};

	request.onreadystatechange = function() {
    if (request.readyState == 4 && request.status == 200) {
              var obj = JSON.parse(request.responseText);
              fileOneReport(obj);
              fileTwoReport(obj);
              listOfUnmatchedFile1(obj);
            listOfUnmatchedFile2(obj);
            }
  };
}

function fileOneReport(arr) {
 $("#file1result p").remove();
 $("#file1result ul").remove();
 var row = '<p class="text-info">File-- ' +arr.fileAnalyses[0].fileName+ ' --Report</p>'+
                    '<ul>' +
                           '<li class="list-group-item"> Total Records: ' + arr.fileAnalyses[0].totalRecords + '</li>' +
                           '<li class="list-group-item" >Matching Records : ' + arr.fileAnalyses[0].matchingRecords + '</li>' +
                           '<li class="list-group-item"> UnMatched Records : ' + arr.fileAnalyses[0].unMatchedRecords + '</li>' +
                     '</ul>';
                 $('#file1result').append(row);
}

function fileTwoReport(arr) {
 $("#file2result p ").remove();
 $("#file2result  ul").remove();
 var row =  '<p class="text-info">File-- ' +arr.fileAnalyses[1].fileName+ ' --Report</p>'+
                    '<ul>' +
                           '<li class="list-group-item"> Total Records: ' + arr.fileAnalyses[1].totalRecords + '</li>' +
                           '<li class="list-group-item" >Matching Records : ' + arr.fileAnalyses[1].matchingRecords + '</li>' +
                           '<li class="list-group-item"> UnMatched Records : ' + arr.fileAnalyses[1].unMatchedRecords + '</li>' +
                     '</ul>';
                 $('#file2result').append(row);
}

function listOfUnmatchedFile1(arr) {
   for(i = 0; i < arr.unMatchedList.length; i++) {
   if(arr.unMatchedList[i].fileName ==arr.fileAnalyses[0].fileName ){
    var row = '<tr>' +
                       '<td>' + arr.unMatchedList[i].date + '</td>' +
                       '<td>' + arr.unMatchedList[i].reference + '</td>' +
                       '<td>' + arr.unMatchedList[i].balance + '</td>' +
                       '<td>' + arr.unMatchedList[i].amount + '</td>' +
                       '</tr>';
                       $('#file1details').append(row);
   }
   }
   }


function listOfUnmatchedFile2(arr) {
   for(i = 0; i < arr.unMatchedList.length; i++) {
     if(arr.unMatchedList[i].fileName ==arr.fileAnalyses[1].fileName ){
                var row1 = '<tr>' +
                    '<td>' + arr.unMatchedList[i].date + '</td>' +
                      '<td>' + arr.unMatchedList[i].reference + '</td>' +
                    '<td>' + arr.unMatchedList[i].balance + '</td>' +
                    '<td>' + arr.unMatchedList[i].amount + '</td>' +
                    '</tr>';
                    $('#file2details').append(row1);
            }
     }
}


function formatErrorMessage(jqXHR, exception) {

    if (jqXHR.status === 0) {
        return ('Not connected.\nPlease verify your network connection.');
    } else if (jqXHR.status == 400) {
        return ('Credit card format invalid. [400]');
    } else if (jqXHR.status == 409) {
        return ('Credit card already created. [409].');
    } else if (jqXHR.status == 500) {
              return ('Internal Server Error. [500].');
    }else if (exception === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else {
        return ('Uncaught Error.\n' + jqXHR.responseText);
    }
}



