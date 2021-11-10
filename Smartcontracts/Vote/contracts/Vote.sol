pragma solidity >=0.4.22 <0.9.0;


contract Vote{

    struct Voter{
        uint weight;
        bool voted;
        uint vote;
    }

    struct Proposal {
        uint count;
    }

    address chairman;

    mapping(address=> Voter) voters;

    Proposal[] proposals;


    modifier onlyChairman(){
        require(msg.sender==chairman);
        _;
    }

    modifier validVoter(){
        require(voters[msg.sender].weight>0, "Not registered");
        _;
    }



    constructor(uint proposalsNum) public {
        chairman = msg.sender;
        voters[chairman].weight = 2;
        for (uint prop=0;prop<proposalsNum;prop++){
            proposals.push(Proposal(0));
        }
    }


    function register(address voter) public onlyChairman {
        require (!voters[voter].voted);

        voters[voter].weight=1;
    }

    function vote(uint proposal) public validVoter{
        Voter memory sender = voters[msg.sender];

        require(!sender.voted);
        require(proposal <proposals.length);

        sender.voted=true;
        sender.vote = proposal;
        proposals[proposal].count +=sender.weight;
    }

    function calcWinner() public validVoter view returns(uint winProposal){
          uint winningCount = 0;

          for(uint prop =0; prop<proposals.length;prop++){
              if (proposals[prop].count > winningCount){
                  winningCount = proposals[prop].count;
                  winProposal = prop;
              }
          }

        assert(winningCount>=5);
    }
}



