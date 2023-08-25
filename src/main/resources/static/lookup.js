function lookup(wordInputId) {
    const word = document.getElementById(wordInputId).value;
    if(word.trim() !== "") {
        window.location.href = `/word-details/${word}`;
    } else {
        alert('Please enter a word.');
    }
}

function attachLookupEvents(inputId, buttonId) {
    document.getElementById(buttonId).addEventListener('click', () => lookup(inputId));

    document.getElementById(inputId).addEventListener('keyup', function(event) {
        if(event.key === 'Enter') {
            lookup(inputId);
        }
    });
}
