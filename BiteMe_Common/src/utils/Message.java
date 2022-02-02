package utils;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The Class Message represents a message in the system (client-server communication).
 */
@SuppressWarnings("serial")
public class Message implements Serializable {
	
	/** The owner. */
	OwnerEnum owner;
	
	/** The op code. */
	OpEnum opCode;
	
	/** The message array L. */
	ArrayList<Object> msgArrayL = new ArrayList<Object>();
	
	
	/**
	 * Instantiates a new message.
	 *
	 * @param owner the owner
	 * @param opCode the op code
	 */
	public Message(OwnerEnum owner, OpEnum opCode) {
		this.owner = owner;
		this.opCode = opCode;
	}
	
	
	/**
	 * Adds the to list.
	 *
	 * @param obj the obj
	 */
	public void addToList(Object obj) {
		msgArrayL.add(obj);
	}
	
	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public OwnerEnum getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(OwnerEnum owner) {
		this.owner = owner;
	}

	/**
	 * Gets the op code.
	 *
	 * @return the op code
	 */
	public OpEnum getOpCode() {
		return opCode;
	}

	/**
	 * Sets the op code.
	 *
	 * @param opCode the new op code
	 */
	public void setOpCode(OpEnum opCode) {
		this.opCode = opCode;
	}

	/**
	 * Gets the message array.
	 *
	 * @return the message array
	 */
	public ArrayList<Object> getMsgArrayL() {
		return msgArrayL;
	}
}
