// from https://stackoverflow.com/questions/9334636/javascript-yes-no-alerts
$(document).on('click', ':not(form)[data-confirm]', function(e){
    if(!confirm($(this).data('confirm'))){
        e.stopImmediatePropagation();
        e.preventDefault();
    }
});

$(document).on('submit', 'form[data-confirm]', function(e){
    if(!confirm($(this).data('confirm'))){
        e.stopImmediatePropagation();
        e.preventDefault();
    }
});

$(document).on('input', 'select', function(e){
    var msg = $(this).children('option:selected').data('confirm');
    if(msg != undefined && !confirm(msg)){
        $(this)[0].selectedIndex = 0;
    }
});

/* USAGE
<!-- hyperlink example -->
<a href="http://www.example.com" data-confirm="Are you sure you want to load this URL?">Anchor</a>

<!-- button example -->
<button type="button" data-confirm="Are you sure you want to click the button?">Button</button>

<!-- form example -->
<form action="http://www.example.com" data-confirm="Are you sure you want to submit the form?">
    <button type="submit">Submit</button>
</form>

<!-- select option example -->
<select>
    <option>Select an option:</option>
    <option data-confirm="Are you want to select this option?">Here</option>
</select>
*/