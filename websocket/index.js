const WebSocket = require('ws');

const wsServer = new WebSocket.Server({
    port: 3000,
});

wsServer.on('connection', (websocket) => {
    websocket.on('message', (message) => {
        switch (message) {
            case 'NEW_CLIENT_CONNECTED': {
                console.log('New client connected - message');
                break;
            }
            case 'CHANGES_MADE': {
                broadcast(websocket, 'CHANGES_MADE');
                break;
            }
            default: { break; }
        }
    });
});

const broadcast = (websocket, message) => {
    wsServer.clients.forEach(client => {
        if (client !== websocket) {
            client.send(message);
        }
    });
}

// For now, forget about it
class ClientsDatabase {
    constructor() {
        this.clients = new Map();
        this.nextId = 0
    }

    addNewClient(newClient) {
        this.findFirstFreeId();
        this.clients.set(this.nextId, newClient)
    }

    findFirstFreeId() {
        this.clients.forEach((value, key) => {
            if (value === null) {
                this.nextId = key;
            }
        });
    }
}