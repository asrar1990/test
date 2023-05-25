package com.bn.device.command.gpb.response;

import com.bn.common.command.gpb.AbstractCommandResponse;

/**
 * Created by sbose on 23/4/23.
 */
public class EndpointGetPassPhraseCommandResponse extends AbstractCommandResponse {

    private String passphrase;

    public EndpointGetPassPhraseCommandResponse(String commandName) {
        super(commandName);
    }

    public String getPassphrase()
    {
        return passphrase;
    }

    public void setPassphrase(String passphrase)
    {
        this.passphrase = passphrase;
    }
}
