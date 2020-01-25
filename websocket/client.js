const connection = new WebSocket('ws://192.168.0.105:3000');

connection.addEventListener('open', () => {
    console.log('connected');
    connection.send('NEW_CLIENT_CONNECTED');
});

connection.addEventListener('message', (message) => {
    console.log(message);
})

const buttond_send = document.querySelector('#send');
buttond_send.addEventListener('click', () => {
    connection.send('CHANGES_MADE');
});