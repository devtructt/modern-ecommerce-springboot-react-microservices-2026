import React from 'react';
import { BAD_REQUEST_ERROR_CODE, INTERNAL_SERVER_ERROR_CODE } from '../../../constant/httpErrorCode';
import { BadRequest } from './BadRequest';
import { InternalServerError } from './InternalServerError';

function HTTPError({ statusCode }) {
    switch (statusCode) {
        case INTERNAL_SERVER_ERROR_CODE:
            return <InternalServerError />;
        case BAD_REQUEST_ERROR_CODE:
            return <BadRequest />;
        default:
            return null;
    }
}

export default HTTPError;