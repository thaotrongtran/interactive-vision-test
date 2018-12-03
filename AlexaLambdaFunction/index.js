/* eslint-disable  func-names */
/* eslint-disable  no-console */
const Alexa = require('ask-sdk-core');
const AWS = require('aws-sdk');
var unirest = require('unirest');


const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return handlerInput.requestEnvelope.request.type === 'LaunchRequest';
    },
    handle(handlerInput) {
        const speechText = "What character is shown?";

        return handlerInput.responseBuilder
            .speak(speechText)
            .getResponse();
    },
};


const visionTestHandler = {
    canHandle(handlerInput) {
        return handlerInput.requestEnvelope.request.type === 'IntentRequest' &&
            handlerInput.requestEnvelope.request.intent.name === 'visiontest';
    },
    handle(handlerInput) {
        console.log(handlerInput);
        const charText = handlerInput.requestEnvelope.request.intent.slots.character.value;
        
        unirest.post('https://eyesight.azurewebsites.net/api/Function1?code=oyuyYMkbi7LsNlFTHcV4XO8uuY9h31WKZqZTaawBwqhRXn5z0w5Q9Q==')
        .headers({'Accept': 'application/json', 'Content-Type': 'application/json'})
        .send({ "character": charText})
        .end(function (response) {
          console.log(response.body);
        });
        
        return handlerInput.responseBuilder
        .speak("You responded with " + charText)
        .reprompt("What else is shown?")
        .getResponse();
    }
};

const CancelAndStopIntentHandler = {
    canHandle(handlerInput) {
        return handlerInput.requestEnvelope.request.type === 'IntentRequest' &&
            (handlerInput.requestEnvelope.request.intent.name === 'AMAZON.CancelIntent' ||
                handlerInput.requestEnvelope.request.intent.name === 'AMAZON.StopIntent');
    },
    handle(handlerInput) {
        const speechText = 'Goodbye!';

        return handlerInput.responseBuilder
            .speak(speechText)
            .withSimpleCard('Goodbye!', speechText)
            .getResponse();
    },
};

const SessionEndedRequestHandler = {
    canHandle(handlerInput) {
        return handlerInput.requestEnvelope.request.type === 'SessionEndedRequest';
    },
    handle(handlerInput) {
        console.log(`Session ended with reason: ${handlerInput.requestEnvelope.request.reason}`);

        return handlerInput.responseBuilder.getResponse();
    },
};

const ErrorHandler = {
    canHandle() {
        return true;
    },
    handle(handlerInput, error) {
        console.log(`Error handled: ${error.message}`);

        return handlerInput.responseBuilder
            .speak('Sorry, I can\'t understand the command. Please say the character again.')
            .reprompt('Sorry, I can\'t understand the command. Please say the character again.')
            .getResponse();
    },
};


const skillBuilder = Alexa.SkillBuilders.custom();

exports.handler = skillBuilder
    .addRequestHandlers(
        LaunchRequestHandler,
        visionTestHandler,
        CancelAndStopIntentHandler,
        SessionEndedRequestHandler
    )
    .addErrorHandlers(ErrorHandler)
    .lambda();
